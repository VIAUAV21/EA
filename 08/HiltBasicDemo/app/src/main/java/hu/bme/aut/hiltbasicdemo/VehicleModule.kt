package hu.bme.aut.hiltbasicdemo

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object VehicleModule {

    @Provides
    fun provideVehicle(
    ): Vehicle {
        return Vehicle("Tesla")
    }
}