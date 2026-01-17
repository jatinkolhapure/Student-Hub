package com.college.campuscollab.service.impl;

import com.college.campuscollab.dto.ProjectUploadRequest;
import com.college.campuscollab.entity.Project;
import com.college.campuscollab.entity.ProjectStatus;
import com.college.campuscollab.entity.User;
import com.college.campuscollab.repository.ProjectRepository;
import com.college.campuscollab.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final FileStorageServiceImpl fileStorageService;

    public ProjectServiceImpl(ProjectRepository projectRepository, FileStorageServiceImpl fileStorageService) {
        this.projectRepository = projectRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public void uploadProject(ProjectUploadRequest request,
                              List<MultipartFile> files,
                              User user) {

        Project project = new Project();
        project.setProjectName(request.getProjectName());
        project.setTeamLeaderName(request.getTeamLeaderName());
        project.setCourse(request.getCourse());
        project.setSemester(request.getSemester());
        project.setTechStack(request.getTechStack());
        project.setDescription(request.getDescription());
        project.setLiveLink(request.getLiveLink());
        project.setCodeLink(request.getCodeLink());
        project.setStatus("PENDING");

        project.setOwner(user);

        List<String> screenshotPaths = new ArrayList<>();

        for (MultipartFile file : files) {
            String path = fileStorageService.save(file); // local or cloud
            screenshotPaths.add(path);
        }

        project.setScreenshots(screenshotPaths);

        projectRepository.save(project);
    }


    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> getProjectsByOwner(User owner) {
        return projectRepository.findByOwner(owner);
    }
}
