package DAO.IDAO;

public interface IProfilePictureDAO {
    String uploadPicture(String username, byte[] imgByteArr); //return link to the profile picture
}
