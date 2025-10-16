package com.example.coursemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coursemanager.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CourseApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseApp(viewModel: CourseViewModel = viewModel(factory = CourseViewModelFactory(LocalContext.current))) {
    val courses by viewModel.courses.collectAsState()
    val selectedCourse by viewModel.selectedCourse
    val showAddDialog by viewModel.showAddDialog

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (selectedCourse != null) "Course Details" else "Course Manager")
                },
                navigationIcon = {
                    if (selectedCourse != null) {
                        IconButton(onClick = { viewModel.clearSelection() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            if (selectedCourse == null) {
                FloatingActionButton(
                    onClick = { viewModel.showAddDialog() }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Course")
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if (selectedCourse == null) {
                CourseListScreen(
                    courses = courses,
                    onCourseClick = { viewModel.selectCourse(it) },
                    onDeleteCourse = { viewModel.deleteCourse(it) }
                )
            } else {
                CourseDetailScreen(
                    course = selectedCourse!!,
                    onDeleteCourse = {
                        viewModel.deleteCourse(it)
                        viewModel.clearSelection()
                    }
                )
            }
        }
    }

    if (showAddDialog) {
        AddCourseDialog(
            onDismiss = { viewModel.hideAddDialog() },
            onAddCourse = { dept, num, loc ->
                viewModel.addCourse(dept, num, loc)
                viewModel.hideAddDialog()
            }
        )
    }
}

@Composable
fun CourseListScreen(
    courses: List<Course>,
    onCourseClick: (Course) -> Unit,
    onDeleteCourse: (Course) -> Unit
) {
    if (courses.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Add your course!",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(courses) { course ->
                CourseItem(
                    course = course,
                    onClick = { onCourseClick(course) },
                    onDelete = { onDeleteCourse(course) }
                )
            }
        }
    }
}

@Composable
fun CourseItem(
    course: Course,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = course.displayName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete Course",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun CourseDetailScreen(
    course: Course,
    onDeleteCourse: (Course) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = course.displayName,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                DetailRow(label = "Department:", value = course.department)
                DetailRow(label = "Course Number:", value = course.courseNumber)
                DetailRow(label = "Location:", value = course.location)
            }
        }

        Button(
            onClick = { onDeleteCourse(course) },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Delete, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Delete Course")
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun AddCourseDialog(
    onDismiss: () -> Unit,
    onAddCourse: (String, String, String) -> Unit
) {
    var department by remember { mutableStateOf("") }
    var courseNumber by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Course") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = department,
                    onValueChange = { department = it },
                    label = { Text("Department") },
                    placeholder = { Text("example:, CS") }
                )
                OutlinedTextField(
                    value = courseNumber,
                    onValueChange = { courseNumber = it },
                    label = { Text("Course Number") },
                    placeholder = { Text("example:, 4530") }
                )
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location") },
                    placeholder = { Text("example:, Room 101") }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (department.isNotBlank() && courseNumber.isNotBlank() && location.isNotBlank()) {
                        onAddCourse(department, courseNumber, location)
                    }
                },
                enabled = department.isNotBlank() && courseNumber.isNotBlank() && location.isNotBlank()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}