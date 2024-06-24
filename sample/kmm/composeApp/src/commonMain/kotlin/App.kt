import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.model.MoveModuleBytecode
import xyz.mcxross.kaptos.model.Option
import xyz.mcxross.kaptos.util.toAccountAddress

@Composable
@Preview
fun App() {
  MaterialTheme {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
      val scrollState = rememberScrollState()
      val coroutineScope = rememberCoroutineScope()
      var modules by remember { mutableStateOf<List<MoveModuleBytecode>>(listOf()) }
      var isLoading by remember { mutableStateOf(false) }
      var errorMessage by remember { mutableStateOf("") }

      Button(
        onClick = {
          coroutineScope.launch {
            isLoading = true
            errorMessage = ""
            try {
              val result = Aptos().getAccountModules("0x1".toAccountAddress())
              if (result is Option.Some) {
                val a =
                  result.value.map {
                    when (it) {
                      is Option.Some -> it.value
                      else -> throw Exception("Invalid module")
                    }
                  }

                modules = a[0]
              } else {
                errorMessage = "No modules found"
              }
            } catch (e: Exception) {
              errorMessage = e.message ?: "An error occurred"
            } finally {
              isLoading = false
            }
          }
        }
      ) {
        Text("Load Modules")
      }

      if (isLoading) {
        CircularProgressIndicator()
      }

      if (errorMessage.isNotEmpty()) {
        Text(errorMessage, color = MaterialTheme.colors.error)
      }

      // Display the modules in a scrollable column
      Column(modifier = Modifier.verticalScroll(scrollState)) {
        modules.forEach { module ->
          Text(module.toString()) // Adjust according to how you want to display each module
        }
      }
    }
  }
}
