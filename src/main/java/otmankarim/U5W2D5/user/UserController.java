package otmankarim.U5W2D5.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import otmankarim.U5W2D5.exceptions.BadRequestException;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String orderBy) {
        return this.userService.getUsers(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable long id) {
        return this.userService.findById(id);
    }

//    @PostMapping    // POST Spostata nel AuthController
//    @ResponseStatus(HttpStatus.CREATED) // Status Code 201
//    public User save(@RequestBody @Validated UserDTO newUserDTO, BindingResult validation) {
//        if (validation.hasErrors()) {
//            throw new BadRequestException(validation.getAllErrors());
//        }
//        return this.userService.save(newUserDTO);
//    }

    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User currentAuthenticatedUser) {
        // @AuthenticationPrincipal mi consente di accedere all'utente attualmente autenticato
        // Posso fare ciò perché precedentemente abbiamo estratto l'id utente dal token e abbiamo cercato
        // nel db l'utente, aggiungendolo poi al Security Context
        return currentAuthenticatedUser;
    }

    @PutMapping("/me")
    public User getMeAndUpdate(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody @Validated UserDTO updatedUser, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.userService.findByIdAndUpdate(currentAuthenticatedUser.getId(), updatedUser);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getMeAndDelete(@AuthenticationPrincipal User currentAuthenticatedUser) {
        this.userService.findByIdAndDelete(currentAuthenticatedUser.getId());
    }

    @PatchMapping("/{id}/uploadAvatar")
    @ResponseStatus(HttpStatus.OK) // Status Code 200
    public String uploadCover(@PathVariable int id, @RequestParam("avatar") MultipartFile image) throws IOException {
        return this.userService.uploadImageAndGetUrl(image, id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User findByIdAndUpdate(@PathVariable int id, @RequestBody @Validated UserDTO updatedUser, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.userService.findByIdAndUpdate(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Status Code 204
    public void findByIdAndDelete(@PathVariable long id) {
        this.userService.findByIdAndDelete(id);
    }
}
