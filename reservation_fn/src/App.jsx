
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter,Route, Routes } from 'react-router-dom';
import LandingPage from './Component/LandingPage';
import AdminLogin from './Component/AdminLogin';
import UserLogin from './Component/UserLogin';
import AdminRegistration from './Component/AdminRegistration';
import AdminHomePage from './Component/AdminHomePage';
import ErrorPage from './Component/ErrorPage';
function App() {
  return (
    <div className="App">
      <BrowserRouter>
      <Routes>
        <Route path='/*' element={<ErrorPage/>}/>
        <Route path='/' element={<LandingPage/>}/>
        <Route path='/adminlogin' element={<AdminLogin/>}/>
        <Route path='/userlogin' element={<UserLogin/>}/>
        <Route path='/adminsignup' element={<AdminRegistration/>}/>
        <Route path='/adminhomepage/*' element={<AdminHomePage/>}/>
      </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
