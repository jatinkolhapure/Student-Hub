package com.college.campuscollab.service;

import com.college.campuscollab.dto.ProjectUploadRequest;
import com.college.campuscollab.entity.Project;
import com.college.campuscollab.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectService {

    void uploadProject(ProjectUploadRequest request,
                       List<MultipartFile> files,
                       User user);


    List<Project> getAllProjects();

    List<Project> getProjectsByOwner(User owner);
}
