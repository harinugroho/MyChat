package es.esy.revolusipingwin.mychat;

/**
 * Created by Hari Nugroho on 24/05/2017.
 */

public class Chat {
    String userId;
    String userName;
    String massage;
    long time;

    /**
     * digunakan untuk membaca data dari firebase dalam bentuk class
     * contoh : Chat invoice = userSnapshot.getValue(Chat.class);
     */
    public Chat() {
    }

    /**
     * digunakan untuk menyimpan data ke firebase dan merupakan constructor untuk Chat
     * @param userName berisi nama yang akan di tampilkan
     * @param userId berisi User Id
     * @param massage berisi pesan
     * @param time berisi waktu pengiriman dalam bentuk unix time stamp
     */
    public Chat(String userName, String userId, String massage, long time) {
        this.userId = userId;
        this.userName = userName;
        this.massage = massage;
        this.time = time;
    }

    /**
     * untuk mendapatkan nama yang akan di tampilkan
     * @return string nama tampilan
     */
    public String getUserName() {
        return userName;
    }

    /**
     * untuk mendapatkan user Id
     * @return string id dari user
     */
    public String getUserId() {
        return userId;
    }

    /**
     * unutk mendapatkan pesan yang ada
     * @return string pesan
     */
    public String getMassage() {
        return massage;
    }

    /**
     * untik mendapatkan waktu
     * @return long waktu dalam bentuk unix time stamp
     */
    public long getTime() {
        return time;
    }
}
