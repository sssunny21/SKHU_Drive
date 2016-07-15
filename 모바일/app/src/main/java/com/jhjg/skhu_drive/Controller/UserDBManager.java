package com.jhjg.skhu_drive.Controller;

import com.jhjg.skhu_drive.Model.UserData;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by kangjungu1 on 2016. 5. 18..
 */
public class UserDBManager {

    private static UserDBManager instance = new UserDBManager();
    private Realm realm;
    private RealmResults<UserData> results;

    public static UserDBManager getInstance() {
        return instance;
    }

    public UserDBManager() {
        realm = Realm.getDefaultInstance();
    }

    public void add(UserData md) {
        realm.beginTransaction();
        realm.copyToRealm(md);
        realm.commitTransaction();
    }

    public void update(UserData md) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(md);
        realm.commitTransaction();
    }

    public void delete(long id) {
        realm.beginTransaction();
        realm.where(UserData.class).equalTo(UserData.FIELD_ID, id).findFirst().removeFromRealm();
        realm.commitTransaction();
    }

    public RealmResults<UserData> getUserDataResult() {
        //조회

        results = realm.where(UserData.class).findAllSorted(UserData.FIELD_ID, Sort.ASCENDING);
        return results;
    }

    public long generateId() {
        results = realm.where(UserData.class).findAllSorted(UserData.FIELD_ID, Sort.ASCENDING);
        if (results.size() == 0) {
            return 0;
        } else {
            return results.get(results.size() - 1).getA_id() + 1;
        }
    }

    public UserData getUserData(long id) {
        return realm.where(UserData.class).equalTo(UserData.FIELD_ID, id).findFirst();

    }


}
