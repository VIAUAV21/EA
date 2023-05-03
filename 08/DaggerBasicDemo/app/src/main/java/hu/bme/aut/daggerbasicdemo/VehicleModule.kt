package hu.bme.aut.daggerbasicdemo

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class VehicleModule(val carType: String) {
    @Provides
    fun provideVehicle() : Vehicle {
        return Vehicle(carType)
    }

    @Provides
    fun provideDetail() : Detail {
        return Detail(5)
    }
}