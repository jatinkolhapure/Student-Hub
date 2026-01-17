package com.college.campuscollab.service;

import com.college.campuscollab.entity.ContributionRequest;
import com.college.campuscollab.entity.Project;
import com.college.campuscollab.entity.User;

import java.util.List;

public interface ContributionRequestService {

    ContributionRequest requestContribution(Project project, User user, String message);

    List<ContributionRequest> getRequestsForProject(Project project);
}
