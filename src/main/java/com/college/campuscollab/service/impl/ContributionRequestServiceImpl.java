package com.college.campuscollab.service.impl;

import com.college.campuscollab.entity.ContributionRequest;
import com.college.campuscollab.entity.Project;
import com.college.campuscollab.entity.RequestStatus;
import com.college.campuscollab.entity.User;
import com.college.campuscollab.repository.ContributionRequestRepository;
import com.college.campuscollab.service.ContributionRequestService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContributionRequestServiceImpl implements ContributionRequestService {

    private final ContributionRequestRepository repository;

    public ContributionRequestServiceImpl(ContributionRequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public ContributionRequest requestContribution(Project project, User user, String message) {
        ContributionRequest request = new ContributionRequest();
        request.setProject(project);
        request.setRequestedBy(user);
        request.setMessage(message);
        request.setStatus(RequestStatus.PENDING);
        request.setRequestedAt(LocalDateTime.now());
        return repository.save(request);
    }

    @Override
    public List<ContributionRequest> getRequestsForProject(Project project) {
        return repository.findByProject(project);
    }
}
