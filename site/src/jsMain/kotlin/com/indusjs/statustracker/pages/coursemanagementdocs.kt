package com.indusjs.statustracker.pages


import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*


@Page(routeOverride = "/coursemanagementdocs")
@Composable
fun CoursePage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.px)
    ) {
        // Course Title
        SpanText(
            "BCA-CC-Semester-1",
            modifier = Modifier.margin(bottom = 16.px)
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.px)
                .boxShadow(blurRadius = 12.px, color = com.varabyte.kobweb.compose.ui.graphics.Colors.Gray)
        ) {
            Column(modifier = Modifier.padding(16.px)) {
                // Course Details (2 Column Layout)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        SpanText("Code: BCA-CC-SEM-1")
                        SpanText("Evaluation Type: Normal")
                        SpanText("Fees Term: C-1 Fees Term")
                        SpanText("Minimum Unit Load: 0.00")
                        SpanText("Short Description: ")
                        SpanText("Full Description: ")
                        SpanText("Department: Faculty of IT and Computer Science")
                    }
                    Column {
                        SpanText("Parent Course: BCA - Cloud Computing")
                        SpanText("Program: BCA - Cloud Computing")
                        SpanText("Maximum Unit Load: 0.00")
                        SpanText("Course Change Fees: ☐")
                        SpanText("Registration Fees: ☑")
                        SpanText("Product: [Admission Fees for C-2] Admission Fees for Course-2")
                        SpanText("Subject Selection: Regular")
                        SpanText("Area: IT & Computer Science")
                        SpanText("Specialization: ")
                    }
                }

                Div(
                    attrs = Modifier.height(24.px).toAttrs()
                )
                // Tabs (Just labels for now)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    SpanText("SUBJECTS", modifier = Modifier.margin(right = 16.px))
                    SpanText("CATEGORIES", modifier = Modifier.margin(right = 16.px))
                    SpanText("GRADING")
                }

                Div(
                    attrs = Modifier.height(24.px).toAttrs()
                )
                // Subjects Table
                Table(
                    attrs = Modifier
                        .fillMaxWidth()
                        .border(1.px, LineStyle.Solid, com.varabyte.kobweb.compose.ui.graphics.Colors.Gray)
                        .toAttrs()
                ) {
                    // Header Row
                    Tr {
                        Th { SpanText("Name") }
                        Th { SpanText("Code") }
                        Th { SpanText("Type") }
                        Th { SpanText("Subject Type") }
                        Th { SpanText("Credit Hours") }
                        Th { SpanText("GradeBook") }
                        Th { SpanText("Department") }
                    }

                    // Data Rows
                    Tr {
                        Td { SpanText("Fundamentals of Programming using C (Theory)") }
                        Td { SpanText("FDPC-T") }
                        Td { SpanText("Both") }
                        Td { SpanText("Compulsory") }
                        Td { SpanText("5.00") }
                        Td { SpanText("GradeBook") }
                        Td { SpanText("Faculty of IT and Computer Science") }
                    }
                    Tr {
                        Td { SpanText("Fundamentals of Programming using C (Practical)") }
                        Td { SpanText("FDPC-P") }
                        Td { SpanText("Practical") }
                        Td { SpanText("Compulsory") }
                        Td { SpanText("5.00") }
                        Td { SpanText("GradeBook") }
                        Td { SpanText("Faculty of IT and Computer Science") }
                    }
                    Tr {
                        Td { SpanText("Digital and Mobile Media Marketing") }
                        Td { SpanText("DMM") }
                        Td { SpanText("Theory") }
                        Td { SpanText("Elective") }
                        Td { SpanText("5.00") }
                        Td { SpanText("GradeBook") }
                        Td { SpanText("Faculty of IT and Computer Science") }
                    }
                    Tr {
                        Td { SpanText("Mathematical Aptitude") }
                        Td { SpanText("MA") }
                        Td { SpanText("Theory") }
                        Td { SpanText("Compulsory") }
                        Td { SpanText("5.00") }
                        Td { SpanText("GradeBook") }
                        Td { SpanText("Faculty of IT and Computer Science") }
                    }

                    // Add line row
                    Tr {
                        Td { SpanText("Add a line") }
                        Td { SpanText("-") }
                        Td { SpanText("-") }
                        Td { SpanText("-") }
                        Td { SpanText("-") }
                        Td { SpanText("-") }
                        Td { SpanText("-") }
                    }
                }
            }
        }
    }
}
