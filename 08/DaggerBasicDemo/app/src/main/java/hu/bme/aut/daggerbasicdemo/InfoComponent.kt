package hu.bme.aut.daggerbasicdemo

import dagger.Component

@Component(modules=[VehicleModule::class])
interface InfoComponent {
    fun inject(app: MainActivity)
}