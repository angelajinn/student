import {createLogger, createStore} from 'vuex';

import students from "./modules/entities/students";

const debug = process.env.NODE_ENV !== 'production';

export default createStore({
  strict: debug,
  plugins: debug ? [createLogger()] : [],
  modules: {
    students
  }
});