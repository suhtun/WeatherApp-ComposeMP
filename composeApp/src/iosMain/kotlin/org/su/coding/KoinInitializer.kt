
import org.koin.core.context.startKoin
import org.su.coding.viewModelModule

actual class KoinInitializer {

    actual fun init(){
        startKoin {
            modules(appModule,viewModelModule)
        }
    }
}