package com.college.campuscollab.controller;

import com.college.campuscollab.dto.ProjectUploadRequest;
import com.college.campuscollab.entity.Project;
import com.college.campuscollab.entity.User;
import com.college.campuscollab.repository.ProjectRepository;
import com.college.campuscollab.service.FileStorageService;
import com.college.campuscollab.service.ProjectService;
import com.college.campuscollab.service.UserService;
import com.college.campuscollab.service.impl.FileStorageServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    public ProjectController(ProjectService projectService,
                             UserService userService, FileStorageServiceImpl fileStorageService) {
        this.projectService = projectService;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    //Upload project (SENIOR)
    private final FileStorageServiceImpl fileStorageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadProject(
            @RequestPart("data") ProjectUploadRequest request,
            @RequestPart("screenshots") List<MultipartFile> files,
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.getUserByEmail(userDetails.getUsername());

        projectService.uploadProject(request, files, user);

        return ResponseEntity.ok("Project uploaded successfully");
    }

    // Explore all projects
    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    // Get my projects
    @GetMapping("/my")
    public List<Project> getMyProjects(
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.getUserByEmail(userDetails.getUsername());
        return projectService.getProjectsByOwner(user);
    }
}
