package com.college.campuscollab.controller;

import com.college.campuscollab.entity.ContributionRequest;
import com.college.campuscollab.entity.Project;
import com.college.campuscollab.entity.User;
import com.college.campuscollab.service.ContributionRequestService;
import com.college.campuscollab.service.ProjectService;
import com.college.campuscollab.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contributions")
public class ContributionController {

    private final ContributionRequestService contributionService;
    private final ProjectService projectService;
    private final UserService userService;

    public ContributionController(ContributionRequestService contributionService,
                                  ProjectService projectService,
                                  UserService userService) {
        this.contributionService = contributionService;
        this.projectService = projectService;
        this.userService = userService;
    }

    //  Request to contribute
    @PostMapping("/{projectId}")
    public ContributionRequest requestContribution(
            @PathVariable Long projectId,
            @RequestBody String message,
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.getUserByEmail(userDetails.getUsername());
        Project project = projectService.getAllProjects()
                .stream()
                .filter(p -> p.getId().equals(projectId))
                .findFirst()
                .orElseThrow();

        return contributionService.requestContribution(project, user, message);
    }

    //  View requests for a project (Owner)
    @GetMapping("/project/{projectId}")
    public List<ContributionRequest> getRequestsForProject(
            @PathVariable Long projectId,
            @AuthenticationPrincipal UserDetails userDetails) {

        User loggedInUser =
                userService.getUserByEmail(userDetails.getUsername());

        Project project = projectService.getAllProjects()
                .stream()
                .filter(p -> p.getId().equals(projectId))
                .findFirst()
                .orElseThrow();

        //  OWNER CHECK
        if (!project.getOwner().getId().equals(loggedInUser.getId())) {
            throw new RuntimeException("Access denied: Not project owner");
        }

        return contributionService.getRequestsForProject(project);
    }

}
