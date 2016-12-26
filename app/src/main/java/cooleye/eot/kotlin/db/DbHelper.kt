package cooleye.eot.kotlin.db

import android.content.Context
import cooleye.eot.kotlin.data.Riding

/**
 * Created by cool on 16-11-25.
 */
object DbHelper {

    private var context: Context? = null

  //  private var sqlite:RidingDb by Delegates.lazy { RidingDb(context!!)}

    public fun instance(ctx: Context): DbHelper {
        this.context = ctx
        return this
    }

    fun saveRidingInfo(data: Riding) {
    }

    fun getRidings():List<Riding>?{
        return null
    }
}