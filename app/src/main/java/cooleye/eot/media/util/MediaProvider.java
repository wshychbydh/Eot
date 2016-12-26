package cooleye.eot.media.util;

import java.util.List;

public interface MediaProvider<T> {
    List<T> getMedias();
}