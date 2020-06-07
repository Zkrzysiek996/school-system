package pl.zborowski.loginjwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zborowski.loginjwt.models.ERole;
import pl.zborowski.loginjwt.models.Grade;
import pl.zborowski.loginjwt.models.Role;
import pl.zborowski.loginjwt.models.User;
import pl.zborowski.loginjwt.payload.GradeRequest;
import pl.zborowski.loginjwt.payload.GradeResponse;
import pl.zborowski.loginjwt.payload.MessageResponse;
import pl.zborowski.loginjwt.repository.GradeRepository;
import pl.zborowski.loginjwt.repository.RoleRepository;
import pl.zborowski.loginjwt.repository.SubjectRepository;
import pl.zborowski.loginjwt.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static pl.zborowski.loginjwt.models.ERole.ROLE_STUDENT;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/grades")
public class GradeController {

    @Autowired
    GradeRepository gradeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    RoleRepository roleRepository;


    @GetMapping
    public ResponseEntity<?> getAllGrades() {
        List<GradeResponse> grades = new ArrayList<>();
        try {
            gradeRepository.findAll().forEach(grade -> {
               grades.add(new GradeResponse(   grade.getId(),
                                               grade.getUser().getId(),
                                               grade.getSubject().getId(),
                                               grade.getGrade(),
                                               grade.getDescription()
               ));
            });
            if(grades.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(grades,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/student/{student_id}")
    public ResponseEntity<?> getUserGrades(@PathVariable Long student_id) {
        List<GradeResponse> gradesUser = new ArrayList<>();
        try {
            gradeRepository.findAll().forEach(grade -> {
                if(grade.getUser().getId() == student_id) {
                    gradesUser.add(new GradeResponse(   grade.getId(),
                                                        grade.getUser().getId(),
                                                        grade.getSubject().getId(),
                                                        grade.getGrade(),
                                                        grade.getDescription()
                    ));
                }
            });
            if(gradesUser.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(gradesUser,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> addGrade(@RequestBody GradeRequest grade) {
        try {
            Set<Role> role = userRepository.findById(grade.getUserId()).get().getRoles();
            if (!role.contains(roleRepository.findByName(ROLE_STUDENT).get())){
                return new ResponseEntity<>(new MessageResponse("User is not a student !"), HttpStatus.NOT_FOUND);
            } else {
                Grade newGrade = gradeRepository.save(new Grade(userRepository.findById(grade.getUserId()).get(),
                        subjectRepository.findById(grade.getSubjectId()).get(),
                        grade.getGrade(),
                        grade.getDescription()));
                GradeResponse newGradeResponse = new GradeResponse(newGrade.getId(), newGrade.getUser().getId(), newGrade.getSubject().getId(), newGrade.getGrade(), newGrade.getDescription());
                return new ResponseEntity<>(newGradeResponse, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteGrade(@PathVariable("id") long id) {
        try {
            gradeRepository.deleteById(id);
            return new ResponseEntity<>(new MessageResponse("Grade deleted successfully!"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }


}
