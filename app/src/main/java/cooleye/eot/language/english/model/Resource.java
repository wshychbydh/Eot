package cooleye.eot.language.english.model;

import io.realm.RealmObject;

/**
 * Created by cool on 16-6-13.
 */
public class Resource extends RealmObject {
    int type = ResourceType.NONE;
    String localPath;
    String serverPath;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }
}
