package com.taskapp.dataaccess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.taskapp.model.Task;
import com.taskapp.model.User;

public class UserDataAccess {
    private final String filePath;

    public UserDataAccess() {
        filePath = "app/src/main/resources/users.csv";
    }

    /**
     * 自動採点用に必要なコンストラクタのため、皆さんはこのコンストラクタを利用・削除はしないでください
     * @param filePath
     */
    public UserDataAccess(String filePath) {
        this.filePath = filePath;
    }

    /**
     * メールアドレスとパスワードを基にユーザーデータを探します。
     * @param email メールアドレス
     * @param password パスワード
     * @return 見つかったユーザー
     */
    public User findByEmailAndPassword(String email, String password) {
        User loginUser = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // ヘッダーを読み飛ばす
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] list = line.split(",");

                // アドレスとパスワードが一致していなかった場合はスキップ
                if (!(list[2].equals(email) && list[3].equals(password))) continue;

                // マッピング
                int code = Integer.parseInt(list[0]);
                String name = list[1];
                String userEmail = list[2];
                String userPassword = list[3];

                loginUser = new User(code, name, userEmail, userPassword);
            }
        } catch (IOException e) {
            e.printStackTrace();;
        }
        return loginUser;
    }

    /**
     * コードを基にユーザーデータを取得します。
     * @param code 取得するユーザーのコード
     * @return 見つかったユーザー
     */
    public User findByCode(int code) {
        User user = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // ヘッダーを読み飛ばす
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] list = line.split(",");

                int userCode = Integer.parseInt(list[0]);
                // codeが一致するか
                if (code != userCode) continue;

                // マッピング
                String name = list[1];
                String email = list[2];
                String password = list[3];
                user = new User(userCode, name, email, password);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }
}
