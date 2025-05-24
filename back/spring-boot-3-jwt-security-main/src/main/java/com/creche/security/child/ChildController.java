package com.creche.security.child;

import com.creche.security.user.User;
import com.creche.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/children")
@RequiredArgsConstructor
public class ChildController {

    private final ChildService service;
    private final UserRepository userRepository;
    @PostMapping("/request")
    public ResponseEntity<String> requestAdmission(@RequestBody ChildRequest request) {
        service.addToWaitingList(request);
        return ResponseEntity.ok("Child request added to waiting list (not stored yet)");
    }

    @GetMapping("/waiting-requests")
    public List<ChildRequest> getWaitingRequests() {
        return service.getWaitingList();
    }

    @PostMapping("/admit-request/{index}")
    public ResponseEntity<Child> admitFromWaiting(@PathVariable Long index) {
        // Récupérer manuellement l'utilisateur avec ID = 2
        User parent = userRepository.findById(2)
                .orElseThrow(() -> new RuntimeException("User with ID=2 not found"));

        Child created = service.admitFromWaitingList(index, parent);
        return ResponseEntity.ok(created);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Child> updateChild(@PathVariable Long id,
                                             @RequestBody ChildUpdateRequest request) {
        Child updatedChild = service.updateChild(id, request);
        return ResponseEntity.ok(updatedChild);
    }




    @GetMapping("/admitted")
    public List<Child> admittedChildren() {
        return service.findAdmittedChildren();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/waiting")
    public List<Child> findWaitingChildrenInDb() {
        return service.findWaitingChildrenInDb();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ChildRequest request,
                                         @AuthenticationPrincipal User parent) {
        service.save(request, parent);
        return ResponseEntity.ok("Child created directly in database");
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody ChildRequest request,
                                         @AuthenticationPrincipal User parent) {
        service.save(request, parent);
        return ResponseEntity.ok("Child updated directly in database");
    }
}
