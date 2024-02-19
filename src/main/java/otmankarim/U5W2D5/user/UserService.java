package otmankarim.U5W2D5.user;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import otmankarim.U5W2D5.exceptions.BadRequestException;
import otmankarim.U5W2D5.exceptions.NotFoundException;

import java.io.IOException;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private Cloudinary cloudinaryUploader;

    public Page<User> getUsers(int pageNumber, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(orderBy));
        return userDAO.findAll(pageable);
    }

    public User save(UserDTO newUserDTO) {
        userDAO.findByEmail(newUserDTO.email()).ifPresent(author -> {
            throw new BadRequestException("L'email " + newUserDTO.email() + " è già in uso!");
        });
        User user = new User(
                newUserDTO.username(),
                newUserDTO.name(),
                newUserDTO.surname(),
                newUserDTO.email(),
                newUserDTO.password()
        );
        user.setAvatar(createAvatarUrl(newUserDTO));
        return userDAO.save(user);
    }

    public User findById(long id) {
        return userDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User findByIdAndUpdate(long id, UserDTO updatedUser) {
        User found = this.findById(id);
        found.setUsername(updatedUser.username());
        found.setName(updatedUser.name());
        found.setSurname(updatedUser.surname());
        found.setEmail(updatedUser.email());
        found.setPassword(updatedUser.password());
        return userDAO.save(found);
    }

    public void findByIdAndDelete(long id) {
        User found = this.findById(id);
        userDAO.delete(found);
    }

    public String createAvatarUrl(UserDTO newUserDTO) {
        return "https://ui-avatars.com/api/?name=" + newUserDTO.name() + "+" + newUserDTO.surname();
    }

    public String uploadImageAndGetUrl(MultipartFile cover, int userId) throws IOException {
        String urlCover = (String) cloudinaryUploader.uploader().upload(cover.getBytes(), ObjectUtils.emptyMap()).get("url");
        User found = findById(userId);
        found.setAvatar(urlCover);
        userDAO.save(found);
        return urlCover;
    }

    public User findByEmail(String email) {
        return userDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("Email " + email + " non trovata"));
    }
}
