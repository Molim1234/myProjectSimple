package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.IncidentEventRepository;
import com.example.demo.repository.IncidentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class EscalationService {

    private final IncidentRepository incidentRepository;
    private final IncidentEventRepository incidentEventRepository;

    @Scheduled(fixedRate = 30000)
    @Transactional
    public void checkIncidentsForEscalation(){

       var list = incidentRepository.findIncidentsByStatus(IncidentStatus.OPEN);
       if(list.isEmpty())
           return;

       for(Incident i:list){
           if(i.getNextEscalationAt()!=null && !i.getNextEscalationAt().isAfter(LocalDateTime.now())){
               var levels = i.getService().getTeam().getEscalationPolicy().getLevels();
               for(EscalationLevel e:levels){
                   if(e.getLevel()==i.getCurrentEscalationLevel()+1){
                       boolean check = false;
                       for(EscalationLevel lev:levels){
                           if(lev.getLevel() == e.getLevel() + 1){
                               check = true;
                               break;
                           }
                       }
                       if(check){
                           i.setNextEscalationAt(LocalDateTime.now().plusMinutes(e.getTimeoutMinutes()));
                       }else{
                           i.setNextEscalationAt(null);
                       }
                       i.setCurrentEscalationLevel(i.getCurrentEscalationLevel()+1);
                       i.setAssignedUser(e.getUser());

                       IncidentEvent createdEvent = new IncidentEvent(
                               i,
                               IncidentEventType.ASSIGNED,
                               "Incident assigned to " + e.getUser().getFirstName() + " " + e.getUser().getLastName()
                       );
                       i.addEvent(createdEvent);
                       incidentEventRepository.save(createdEvent);
                       incidentRepository.save(i);
                       break;
                   }
               }
           }
       }

    }


}
