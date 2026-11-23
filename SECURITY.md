# Spring Security

The API uses stateless JWT authentication with two roles: `ADMIN` and `USER`.

## Configuration

Set these environment variables before starting the application:

- `JWT_SECRET`: a random secret containing at least 32 bytes. It signs and verifies access tokens.
- `APP_BOOTSTRAP_SECRET`: a separate one-time secret used to create the first administrator.

The values in `application.properties` are local-development defaults only. Replace them outside local development. Do not commit real secrets.

PowerShell example:

```powershell
$env:JWT_SECRET = "<your-random-secret-at-least-32-bytes>"
$env:APP_BOOTSTRAP_SECRET = "<your-separate-bootstrap-secret>"
```

## Create the first administrator

The bootstrap endpoint can create an administrator only while the database has no administrator role assigned. It is protected by `APP_BOOTSTRAP_SECRET`. If the submitted email already belongs to a legacy user, that account is upgraded to the first administrator and receives the supplied password. Otherwise, the endpoint creates the initial team, its empty escalation policy, and the administrator.

`POST /api/auth/bootstrap`

```json
{
  "teamName": "Platform",
  "firstName": "Admin",
  "lastName": "User",
  "email": "admin@example.com",
  "password": "a-long-password-of-at-least-12-chars",
  "bootstrapSecret": "<same-value-as-APP_BOOTSTRAP_SECRET>"
}
```

The response contains an access token and a refresh token. Bootstrap cannot create another administrator once one already exists.

## Login, refresh, and logout

- `POST /api/auth/login` with `email` and `password` returns an access/refresh token pair.
- Access tokens are valid for 15 minutes by default.
- Refresh tokens are random opaque values, stored in the database only as SHA-256 hashes, and valid for 7 days by default.
- `POST /api/auth/refresh` accepts `{"refreshToken":"..." }` and rotates the refresh token. The old refresh token becomes unusable.
- `POST /api/auth/logout` accepts the refresh token and revokes it. A previously issued access token remains valid until its short expiry (15 minutes by default).

Send the access token on protected requests:

```
Authorization: Bearer <access-token>
```

## Authorization

- Public: `POST /api/auth/bootstrap`, `POST /api/auth/login`, `POST /api/auth/refresh`, `POST /api/auth/logout`.
- Public health probe: `GET /actuator/health`.
- Administrator only: all `/api/users/**`, `/api/teams/**`, and `/api/escalation-policies/**` routes, plus write operations on `/api/services/**`.
- All other API routes require a valid access token.

New users are created by an administrator through `POST /api/users`; passwords must be at least 12 characters. New accounts receive the `USER` role. The first administrator is created only through bootstrap. User passwords are stored using BCrypt (strength 12), never as plain text.

An administrator can set or reset a user's password through `PATCH /api/users/{userId}/password`. This also revokes that user's refresh tokens. This endpoint is useful for legacy users whose accounts existed before password authentication was introduced.

## Database compatibility

The `password_hash` column is nullable so existing user rows do not prevent the application from starting after the update. Those legacy accounts cannot log in until an administrator sets a password. Existing rows receive the `USER` role by default.
