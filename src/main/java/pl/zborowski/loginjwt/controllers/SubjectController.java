package pl.zborowski.loginjwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zborowski.loginjwt.models.Subject;
import pl.zborowski.loginjwt.payload.GradeResponse;
import pl.zborowski.loginjwt.payload.MessageResponse;
import pl.zborowski.loginjwt.payload.SubjectRequest;
import pl.zborowski.loginjwt.payload.SubjectResponse;
import pl.zborowski.loginjwt.repository.SubjectRepository;
import pl.zborowski.loginjwt.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    UserRepository userRepository;


    @GetMapping
    public ResponseEntity<?> getAllSubject() {
        List<SubjectResponse> subjects = new ArrayList<>();
        try {
            subjectRepository.findAll().forEach(subject -> {
                subjects.add(new SubjectResponse(   subject.getId(),
                                                    subject.getSubjectName(),
                                                    subject.getTeacher().getId()
                ));
            });
            if(subjects.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(subjects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/teachers/{teacher_id}")
    public ResponseEntity<?> getTeacherSubjects(@PathVariable Long teacher_id) {
        List<SubjectResponse> subjectsTeacher = new ArrayList<>();
        try {
            subjectRepository.findAll().forEach(subject -> {
                if(subject.getTeacher().getId() == teacher_id) {
                    subjectsTeacher.add(new SubjectResponse(    subject.getId(),
                                                                subject.getSubjectName(),
                                                                subject.getTeacher().getId()
                    ));
                }
            });
            if(subjectsTeacher.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(subjectsTeacher, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> addSubject(@RequestBody SubjectRequest subject) {
        try {
            if (subjectRepository.findBySubjectName(subject.getSubjectName()).isPresent()) {
                return new ResponseEntity<>(new MessageResponse("This subject already exists!"), HttpStatus.ALREADY_REPORTED);
            } else {
                Subject newSubject = subjectRepository.save(new Subject(
                        subject.getSubjectName(),
                        (userRepository.findById(subject.getTeacherId())).get()
                ));
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

}
