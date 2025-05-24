package com.creche.security.child;

import com.creche.security.user.Role;
import com.creche.security.user.User;
import com.creche.security.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChildService {

    public void addToWaitingList(ChildRequest request) {
        waitingList.add(request);
    }

    private final ChildRepository repository;
    private final UserRepository userRepository;

    @Getter
    private final List<ChildRequest> waitingList = new ArrayList<>();

    public void save(ChildRequest request, User parent) {
        String[] nameParts = request.getFullName().trim().split(" ", 2);
        String firstName = nameParts.length > 0 ? nameParts[0] : "";
        String lastName = nameParts.length > 1 ? nameParts[1] : "";

        Child child = Child.builder()
                .firstName(firstName)
                .lastName(lastName)
                .dateOfBirth(LocalDate.parse(request.getDateOfBirth()))
                .allergies(request.getAllergies())
                .specialNeeds(request.getSpecialNeeds())
                .admitted(false)
                .parent(parent)
                .build();

        repository.save(child);
    }
    @Transactional

    public Child admitFromWaitingList(Long index, User parent) {
        if (index == null || index < 0 || index >= waitingList.size()) {
            throw new IllegalArgumentException("Index invalide");
        }
        if (parent == null) {
            throw new IllegalArgumentException("Parent ne peut pas être null");
        }

        ChildRequest request = waitingList.remove(index.intValue());

        String[] nameParts = request.getFullName().trim().split(" ", 2);
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? nameParts[1] : "";

        Child child = Child.builder()
                .firstName(firstName)
                .lastName(lastName)
                .dateOfBirth(LocalDate.parse(request.getDateOfBirth()))
                .allergies(request.getAllergies())
                .specialNeeds(request.getSpecialNeeds())
                .admitted(true)
                .parent(parent)
                .build();

        Child savedChild = repository.save(child);

        // Assurez-vous que l'utilisateur est bien rechargé depuis la BDD avant de modifier son rôle
        Optional<User> optionalUser = userRepository.findById(parent.getId());
        if (optionalUser.isPresent()) {
            User persistentParent = optionalUser.get();
            if (persistentParent.getRole() == Role.GUEST) {
                persistentParent.setRole(Role.PARENT);
                userRepository.save(persistentParent);
            }
        } else {
            throw new IllegalArgumentException("Parent non trouvé");
        }

        return savedChild;
    }

    public List<Child> findAdmittedChildren() {
        return repository.findByAdmittedTrue();
    }

    public List<Child> findWaitingChildrenInDb() {
        return repository.findByAdmittedFalse();
    }


    public Child updateChild(Long childId, ChildUpdateRequest request) {
        Child child = repository.findById(childId)
                .orElseThrow(() -> new IllegalArgumentException("Enfant non trouvé avec l'ID : " + childId));

        if (request.getFirstName() != null) child.setFirstName(request.getFirstName());
        if (request.getLastName() != null) child.setLastName(request.getLastName());
        if (request.getDateOfBirth() != null) child.setDateOfBirth(LocalDate.parse(request.getDateOfBirth()));
        if (request.getAllergies() != null) child.setAllergies(request.getAllergies());
        if (request.getSpecialNeeds() != null) child.setSpecialNeeds(request.getSpecialNeeds());

        return repository.save(child);
    }


    public void delete(Long id) {
        repository.deleteById(id);
    }
}
