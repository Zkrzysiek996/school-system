package pl.zborowski.loginjwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zborowski.loginjwt.models.Class;
import pl.zborowski.loginjwt.models.Subject;
import pl.zborowski.loginjwt.models.User;
import pl.zborowski.loginjwt.payload.ClassRequest;
import pl.zborowski.loginjwt.payload.ClassResponse;
import pl.zborowski.loginjwt.payload.MessageResponse;
import pl.zborowski.loginjwt.repository.ClassRepository;
import pl.zborowski.loginjwt.repository.SubjectRepository;
import pl.zborowski.loginjwt.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/classes")
public class ClassController {

    @Autowired
    ClassRepository classRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getAllClasses() {
        List<ClassResponse> classes = new ArrayList<>();
        try {
            classRepository.findAll().forEach(aClass -> {
                classes.add(new ClassResponse(  aClass.getId(),
                                                aClass.getClassName(),
                                                aClass.getStudents(),
                                                aClass.getSubjects()
                ));
            });
            if(classes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(classes,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/subjects/{subject_id}")
    public ResponseEntity<?> getSubjectClasses(@PathVariable Long subject_id) {
        List<ClassResponse> classesSubject = new ArrayList<>();
        try {
            classRepository.findAll().forEach(aClass -> {
                if(aClass.getSubjects().contains(subjectRepository.findById(subject_id).get())) {
                    classesSubject.add(new ClassResponse(   aClass.getId(),
                                                            aClass.getClassName(),
                                                            aClass.getStudents(),
                                                            aClass.getSubjects()
                    ));
                }
            });
            if(classesSubject.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(classesSubject, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> addClass(@RequestBody ClassRequest aClass) {
        try {
            if(classRepository.findClassByClassName(aClass.getClassName()).isPresent()) {
                return new ResponseEntity<>(new MessageResponse("This class already exists!"), HttpStatus.ALREADY_REPORTED);
            } else {
                List<User> classStudents = new ArrayList<>();
                List<Subject> classSubject = new ArrayList<>();
                aClass.getStudents().forEach(studentId -> classStudents.add(userRepository.findById(studentId).get()));
                aClass.getSubjects().forEach(subjectId -> classSubject.add(subjectRepository.findById(subjectId).get()));
                Class newClass = classRepository.save(new Class(
                        aClass.getClassName(),
                        classStudents,
                        classSubject
                ));
                return new ResponseEntity<>(newClass,HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }
    }
}
