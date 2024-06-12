package Database;

import java.security.SecureRandom;

public class RoomCodeGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();

    public static String generateUniqueRoomCode() {
        StringBuilder roomCode = new StringBuilder(6);
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(CHARACTERS.length());
            roomCode.append(CHARACTERS.charAt(index));
        }
        return roomCode.toString();
    }

    public static void main(String[] args) {
        System.out.println(generateUniqueRoomCode());
    }
}
