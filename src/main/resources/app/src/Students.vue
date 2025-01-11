
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


    <div v-if="studentCreated">
      <div class="create-form">
        <hr class="new1">
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
    </div>

    <div v-if="editingStudent">
      <div class="create-form">

        <hr class="new1">

        <h2>Edit Student</h2>
        <form @submit.prevent="updateStudent">
          <div class="form-row">
            <div class="form-group">
              <label for="firstName">First name:</label>
              <input type="text" required placeholder="first name..." v-model="editingStudent.firstName">
            </div>

            <div class="form-group">
              <label for="lastName">Last name:</label>
              <input type="text" placeholder="last name..." v-model="editingStudent.lastName">
            </div>

            <div class="form-group">
              <label for="dob">Date of birth:</label>
              <input type="date" placeholder="dob..." v-model="editingStudent.dateOfBirth">
            </div>

          </div>

          <div class="form-row">
            <div class = "form-group">
              <label for="program">Program:</label>
              <select v-model="editingStudent.program">
                <option disabled value="">Please select one</option>
                <option v-for="program in allPrograms" :key="program.id" :value="program.id">
                  {{ program.name }}
                </option>
              </select>
            </div>

            <div class="form-group">
              <label for="gpa">GPA:</label>
              <input type="number" placeholder="gpa..." v-model="editingStudent.gpa" step="0.01">
            </div>
          </div>
          <div class="edit-form-button">
            <button type="submit">Update student</button>
            <button type="button" @click="cancelEdit">Cancel</button>
          </div>
      </form>
    </div>
</div>


      <div class="search-wrapper">
        <div class="search-fields">
          <input class="form-control" v-model="filters.firstName.value" placeholder="Filter by First Name"/>
          <input class="form-control" v-model="filters.lastName.value" placeholder="Filter by Last Name"/>
          <select v-model="filters.program.value" class="form-control">
            <option value="">Filter by Program</option>
            <option v-for="program in allPrograms" :key="program.id" :value="program.id">
              {{program.name}}
            </option>
          </select>
        </div>
      </div>

      <div class="table-wrapper">
        <table>
          <thead>
          <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Date of Birth</th>
            <th>GPA</th>
            <th>Program</th>
            <th>Courses</th>
            <th>Actions</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="student in filteredStudents" :key="student.id">
            <td>{{ student.firstName }}</td>
            <td>{{ student.lastName }}</td>
            <td>{{ student.dateOfBirth }}</td>
            <td>{{ student.gpa }}</td>
            <td>{{ student.program ? student.program.name : 'N/A' }}</td> <!-- Handle Program association -->
            <td>
              <ul>
                <li v-for="course in student.courses" :key="course.id">
                  {{course.course.name}}
<!--                  take course type from StudentCourse-->
                </li>
              </ul>
            </td>
            <td>
              <button @click="editStudent(student)">Edit</button>
              <button @click="deleteStudent(student.id)">Delete</button>
            </td>
          </tr>
          </tbody>
        </table>
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

      filters: {
        firstName: { value: '' },
        lastName: { value: '' },
        program: { value: '' },
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

  computed: {
    filteredStudents() {
      return this.allStudents.filter(student => {
        const matchFirstName = this.filters.firstName.value
            ? student.firstName.toLowerCase().includes(this.filters.firstName.value.toLowerCase())
            : true;

        const matchLastName = this.filters.lastName.value
            ? student.lastName.toLowerCase().includes(this.filters.lastName.value.toLowerCase())
            : true;

        const matchProgram = this.filters.program.value
            ? student.program && student.program.id === this.filters.program.value
            : true;
        return matchFirstName && matchLastName && matchProgram;
      });
    },
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

    cancelEdit() {
      this.editingStudent = null;
    },

    fetchStudents() {
      fetch('/api/student/')
          .then(response => response.json()) //  parses JSON
          .then(data => {
            this.allStudents = data.reverse(); // Populate allStudents array
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
      fetch(`/api/student-courses/${this.studentId}/enroll`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ courseIds: this.coursesToAdd.map(course => course.id) }),
      })
          // .then(response => response.json()) don't need this since 'enroll' function in controller return VOID -> no JSON response
          .then(data => {
            this.fetchStudents();
            this.coursesToAdd = [];
            this.studentCreated = false;
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
  margin-bottom: 200px;
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

.edit-form-button {
  display:flex;
  gap: 20px;
}

.search-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 20px;
  font-family: 'Raleway', sans-serif;
}

.search-fields {
  display: flex;
  height: 45px;
  gap: 20px; /* Adds spacing between the two fields */
  justify-content: left; /* Ensures the fields are centered */
}

.search-fields input,
.search-fields select {
  height: 45px /* Ensure all fields have the same width */
}


.table-wrapper {
  width: 100%; /* Fixed width */
  max-width: 1200px;
  max-height: 400px; /* Maximum height */
  overflow-y: auto; /* Enable scrolling for excess rows */
  margin: 20px auto;
  display: flex;
  font-family: 'Raleway', sans-serif;
}

table {
  width: 100%; /* Ensures table takes up the full width of its container */
  table-layout: fixed; /* Prevents column resizing based on content */
  border-collapse: collapse;
}

th {
  position: sticky;
  top: 0;
  background-color: #B0D1B8;
  z-index: 1;
  height: 50px;
}

td, td {
  padding: 8px;
  text-align: center;
  border-bottom: 1px solid #ddd;
}

hr.new1 {
  border-top: 1px solid #f1f1f1;
}


</style>