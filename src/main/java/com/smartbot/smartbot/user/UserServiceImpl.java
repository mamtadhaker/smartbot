package com.smartbot.smartbot.user;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smartbot.smartbot.entity.User;
import com.smartbot.smartbot.exception.UserNotFoundException;
import com.smartbot.smartbot.recognition.FaceRecognitionRequest;
import com.smartbot.smartbot.recognition.FaceRecognitionResponse;
import com.smartbot.smartbot.recognition.FaceRecognitionService;
import com.smartbot.smartbot.recognition.FaceStoreRequest;
import com.smartbot.smartbot.utils.Constants;

@Service
public class UserServiceImpl implements UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private FaceRecognitionService faceRecognitionService;

  @Value("${aws.s3.bucket.users}")
  private String faceRecognitionCollection;

  // @Override
  // public List<User> findAll() {
  //   List<User> users = new ArrayList<User>();
  //   userRepository.findAll().forEach(users::add);
  //   return users;
  // }

  // @Override
  // public User getUser(String id) {
  //   return userRepository.findOne(id);
  // }

  // @Override
  // public void addUser(User user) {
  //   userRepository.save(user);
  // }

  // @Override
  // public void updateUser(String uuid, User user) {
  //   userRepository.save(user);
  // }

  // @Override
  // public void deleteUser(UUID id) {
  //   userRepository.delete(id);
  // }

  @Override
  public User saveUser(UserStoreDTO userStoreDTO) {
    User user = new User(userStoreDTO.getFirstName(), userStoreDTO.getLastName());
    user = userRepository.save(user);
    String imageName = user.getUuid() + Constants.UNDERSCORE_SEPRATOR + "1";
    faceRecognitionService
        .saveImage(new FaceStoreRequest(userStoreDTO.getImage(), faceRecognitionCollection, imageName));
    return user;
  }

  @Override
  public UserDTO getUserByImage(MultipartFile image) {

    UserDTO userDTO = null;
    boolean isNewUser = true;
    LOGGER.info("Searching for image");
    //get user UUID from image recognition service
    FaceRecognitionResponse faceRecognitionResponse = faceRecognitionService
        .searchImage(new FaceRecognitionRequest(image, faceRecognitionCollection));

    if (MapUtils.isNotEmpty(faceRecognitionResponse.getRecognisedUsersMap())) {
      LOGGER.info("Users found for image");
      User user = getUserByUuid(faceRecognitionResponse.getRecognisedUsersMap().keySet().iterator().next());
      if (null == user) {
        userDTO = new UserDTO(createNewUser(image));
      } else {
        LOGGER.info("User Id matched : {}", user.getId());
        isNewUser = false;
        userDTO = new UserDTO(user);
      }
    } else {
      userDTO = new UserDTO(createNewUser(image));
    }
    userDTO.setIsNewUser(isNewUser);
    return userDTO;
  }

  //Create and store new user and set his image as provided in the input.
  private User createNewUser(MultipartFile image) {
    UserStoreDTO userStoreDTO = new UserStoreDTO(Constants.NEW_USER_FIRST_NAME, Constants.NEW_USER_LAST_NAME, image);

    return saveUser(userStoreDTO);
  }

  @Override
  public User getUserByUuid(String uuid) throws UserNotFoundException {
    return userRepository.findByUuid(uuid);
  }

}