
<template>
  <div class="page-container">
    <div>
      <h2 style="text-align: center; font-family: 'Raleway', sans-serif; font-size: 45px; line-height: 0.3px; ">Sample App</h2>
      <h3 style="text-align: center; font-family: 'Raleway', sans-serif; font-size: 20px; margin-bottom: 50px; ">Student Info</h3>
    </div>

    <div class="create-form">
      <form @submit.prevent="createStudent">
        <div class="form-row">
          <div class="form-group">
            <label for="firstName">First name:</label>
              <input type="text" required placeholder="first name..." v-model="newStudent.firstName">
          </div>

          <div class="form-group">
            <label for="lastName">Last name:</label>
            <input type="text" placeholder="last name..." v-model="newStudent.lastName">
          </div>

          <div class="form-group">
            <label for="dob">Date of birth:</label>
            <input type="date" placeholder="dob..." v-model="newStudent.dateOfBirth">
          </div>

        </div>

        <div class="form-row">
          <div class = "form-group">
            <label for="program">Program:</label>
            <select v-model="newStudent.program">
              <option disabled value="">Please select one</option>
              <option v-for="program in allPrograms" :key="program.id" :value="program.id">
                {{ program.name }}
              </option>
            </select>
          </div>

          <div class="form-group">
            <label for="gpa">GPA:</label>
            <input type="number" placeholder="gpa..." v-model="newStudent.gpa" step="0.01">
          </div>

        </div>

        <button type="submit">Add student</button>
      </form>
    </div>

    <div>
      <h2>All Students</h2>
      <ul>
        <table>
          <thead>
          <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Date of Birth</th>
            <th>GPA</th>
            <th>Program</th>
<!--            <th>Courses</th>-->
            <th>Actions</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="student in allStudents" :key="student.id">
            <td>{{ student.firstName }}</td>
            <td>{{ student.lastName }}</td>
            <td>{{ student.dateOfBirth }}</td>
            <td>{{ student.gpa }}</td>
            <td>{{ student.program ? student.program.name : 'N/A' }}</td> <!-- Handle Program association -->
<!--            <td>{{ student.course ? student.course.name : 'N/A' }}</td>-->
            <td>
              <button @click="editStudent(student)">Edit</button>
              <button @click="deleteStudent(student.id)">Delete</button>
            </td>
          </tr>
          </tbody>
        </table>

      </ul>
    </div>

    <div v-if="studentCreated">
      <h2>Add Courses to Student</h2>
      <form @submit.prevent="addCoursesToStudent">
          <label for="course">Courses:</label>
          <multiselect
              v-model="coursesToAdd"
              :options="allCourses"
              :multiple="true"
              track-by="id"
              label="name"
              placeholder="Select courses"
          />
        <button type="submit">Add Courses</button>
      </form>
    </div>

    <div v-if="editingStudent">
      <h2>Edit Student</h2>
      <form @submit.prevent="updateStudent">
        <p>
          First name:
          <input type="text" required placeholder="first name..." v-model="editingStudent.firstName">
        </p>
        <p>
          Last name:
          <input type="text" placeholder="last name..." v-model="editingStudent.lastName">
        </p>
        <p>
          Date of birth:
          <input type="date" placeholder="dob..." v-model="editingStudent.dateOfBirth">
        </p>
        <p>
          GPA:
          <input type="number" placeholder="gpa..." v-model="editingStudent.gpa" step="0.01">
        </p>
        <p>
          Program:
          <select v-model="editingStudent.program">
            <option disabled value="">Please select one</option>
            <option v-for="program in allPrograms" :key="program.id" :value="program.id">
              {{ program.name }}
            </option>
          </select>
        </p>
        <button type="submit">Update student</button>
      </form>
    </div>
  </div>
</template>

<script>

import Multiselect from "vue-multiselect";
import "vue-multiselect/dist/vue-multiselect.min.css";

export default {
  name: "App",
  components: {Multiselect},
  data() {
    return {
      newStudent: {
        firstName: '',
        lastName: '',
        dateOfBirth: '',
        gpa: '',
        program: '',
        course: [],
      },
      coursesToAdd: [],
      allStudents: [], // array that stores the list of all students
      editingStudent: null,
      allPrograms: [],
      allCourses: [],
      studentCreated: false,
      studentId: null,
    };
  },
  created() {
    this.fetchStudents();
    this.fetchPrograms();
    this.fetchCourses();
  },
  methods: {
    editStudent(student) { // set the student to be edited
      this.editingStudent = {...student};
      this.editingStudent.program = student.program ? student.program.id : null;
    },

    fetchStudents() {
      fetch('/api/student/')
          .then(response => response.json()) //  parses JSON
          .then(data => {
            this.allStudents = data; // Populate allStudents array
          });
    },

    updateStudent() {
      this.editingStudent.program = this.editingStudent.program ? {id: this.editingStudent.program} : null;
      fetch(`/api/student/${this.editingStudent.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(this.editingStudent),
      })
          .then(response => response.json()) // Handle response
          .then(() => {
            this.fetchStudents();
            this.editingStudent = null;
          });
    },

    deleteStudent(studentId) {
      fetch(`/api/student/${studentId}`, {
        method: "DELETE",
      })
          .then(() => {
            this.allStudents = this.allStudents.filter(
                (student) => student.id !== studentId
            );
          })
          .catch((error) => {
            console.error("Cannot delete student (error):", error);
          });
    },

    createStudent() {
      fetch("/api/student", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          // 'Content-Type': 'application/x-www-form-urlencoded',v
        },
        body: JSON.stringify({
          firstName: this.newStudent.firstName,
          lastName: this.newStudent.lastName,
          dateOfBirth: this.newStudent.dateOfBirth,
          gpa: this.newStudent.gpa,
          program: {
            id: this.newStudent.program // send program ID
          },
          courses: this.newStudent.course.map((course) => course.id),
        }), // body data type must match "Content-Type" header
      })
          // when sending a student to the API, the backend responds in JSON format. "response.json() makes sure that
          // the body of the response is parsed into a JS object
          .then(response => response.json()) // Handle response
          .then(data => {
            this.studentId = data.id;
            this.studentCreated = true;
            this.fetchStudents(); // Refresh the student list after adding a new student (fetch the latest list)
            // Clear the input fields after successful submission
            this.newStudent = {firstName: '', lastName: '', dateOfBirth: '', gpa: '', program: '', course: []};
          });
    },

    addCoursesToStudent() {
      fetch(`/api/student/${this.studentId}/add-courses`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ courseIds: this.coursesToAdd.map(course => course.id) }),
      })
          .then(response => response.json())
          .then(data => {
            this.fetchStudents();
            this.coursesToAdd = [];
          })
    },

    fetchPrograms() {
      fetch('/api/program/')
          .then(response => response.json())
          .then(data => {
            this.allPrograms = data;
          });
    },

    fetchCourses() {
      fetch('/api/course/')
          .then(response => response.json())
          .then(data => {
            this.allCourses = data;
          });
    },
  }
}
</script>

<style scoped>

.create-form {
  max-width: 1000px;
  margin: 20px auto;
  font-family: 'Raleway', sans-serif;
}

form {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.form-row {
  display: flex;
  gap: 20px;
}

.form-group {
  flex: 1;
  display: flex;
  flex-direction: column;
}

label {
  margin-bottom: 2px;
  font-weight: bold;
  font-size: 14px;
}

input, select {
  padding: 10px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
  width: 296px;
}

.page-container {
  margin-left: 50px;
  margin-right: 50px;
}

input[type=date],
select {
  width: 296px;
  height: 43px;
  font-size: 13px;
  padding: 12px 20px;
  margin: 8px 0;
  box-sizing: border-box;
  border-radius: 10px;
}

input[type=text],
input[type=number],
select {
  width: 296px;
  font-size: 14px;
  padding: 12px 20px;
  margin: 8px 0;
  box-sizing: border-box;
  border-radius: 10px;
}

button {
  margin-top: 20px;
  padding: 10px 20px;
  font-size: 14px;
  background-color: #D2E7D8;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  width: 147px;
}

button:hover {
  background-color: #B0D1B8;
}

</style>