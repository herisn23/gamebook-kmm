package routing

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import cast
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1

interface RoutePathVars

interface NoPathVars : RoutePathVars

data class Route<T : RoutePathVars>(
    private val separator: String = "/",
    private val indexes: List<RouteIndex>
) {
    data class RouteIndex(
        val name: String,
        val type: NavType<*>? = null
    )

    data class Navigate<T : RoutePathVars>(
        private val ctrl: NavController,
        private val route: Route<T>,
        private val builder: PathValuesBuilder<T>
    ) {


        class PathValuesBuilder<T : RoutePathVars> {
            private val _values: MutableMap<KProperty1<T, *>, Any> = mutableMapOf()

            fun <V : Any> add(prop: KProperty1<T, V>, v: V) =
                apply {
                    _values[prop] = v
                }

            val values: Map<KProperty1<T, *>, Any> get() = _values
        }

        fun navigate() {
            val plainValues = builder.values.map { (key, value) ->
                key.name to value
            }.toMap()
            ctrl.navigate(route.buildDynamicRoute {
                plainValues.getValue(it).toString()
            })
        }
    }

    val route by lazy {
        buildDynamicRoute { "{$it}" }
    }

    private val pathVariables by lazy {
        indexes.mapNotNull {
            it.type?.let { type ->
                it.name to type
            }
        }.toMap()
    }

    fun composable(
        graph: NavGraphBuilder,
        content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
    ) {
        graph.composable(
            route = route,
            content = content,
            arguments = pathVariables.map { (key, t) ->
                navArgument(key) { type = t }
            }
        )
    }

    fun navigate(ctrl: NavController, build: Navigate.PathValuesBuilder<T>.() -> Unit = {}) =
        Navigate(ctrl, this, Navigate.PathValuesBuilder<T>().apply(build)).navigate()


    private fun buildDynamicRoute(varReplace: (String) -> String): String =
        indexes.joinToString(separator, prefix = separator) {
            when (it.type != null) {
                true -> varReplace(it.name)
                false -> it.name
            }
        }
}

class RouteBuilder<T : RoutePathVars> {

    private val pathVariables = mutableListOf<Route.RouteIndex>()

    fun addPathVariable(prop: KProperty1<T, *>, navType: NavType<*>? = null) =
        apply {
            pathVariables.add(Route.RouteIndex(prop.name, navType))
        }

    fun addPathVariable(name: String) =
        apply {
            pathVariables.add(Route.RouteIndex(name))
        }


    fun build(): Route<T> =
        Route(
            indexes = pathVariables
        )

}

inline operator fun <reified T> NavBackStackEntry?.getValue(
    thisRef: Any?,
    property: KProperty<*>
): T {
    return this?.arguments?.get(property.name).cast()
}