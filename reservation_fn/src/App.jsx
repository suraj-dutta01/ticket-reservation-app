
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter,Route, Routes, useLocation } from 'react-router-dom';
import LandingPage from './Component/LandingPage';
import AdminLogin from './Component/AdminLogin';
import UserLogin from './Component/UserLogin';
import AdminRegistration from './Component/AdminRegistration';
import AdminHomePage from './Component/AdminHomePage';
import ErrorPage from './Component/ErrorPage';
import ResetPassword from './Component/ResetPassword';
import ForgotPassword from './Component/ForgotPassword';
import Loader from './Component/Loader';
import { useEffect, useState } from 'react';
import axios from 'axios';
import UserHomePage from './Component/UserHomePage';
import Protect from './Component/Protect';
function App() {
    let[loading,setLoading]=useState(false)
    // let location=useLocation();
    // useEffect(()=>{
    //     setLoading(true)
    //     const handleComplete=()=> setLoading(false);
    //     const time=setTimeout(handleComplete,500);
    //     return ()=>{
    //       clearTimeout(time);
    //     }
    // },[location])
    useEffect(()=>{
      const requestInterseptor=  axios.interceptors.request.use(
          (config)=>{
            setLoading(true)
            return config
          },(err)=>{
            return Promise.reject(err);
          })

      const responseInterseptor= axios.interceptors.response.use(
            (response)=>{
              setLoading(false)
              return response
            },(err)=>{
              setLoading(false)
              return Promise.reject(err);
            })   

      // return ()=>{
      //   axios.interceptors.request.eject(requestInterseptor);
      //   axios.interceptors.response.eject(responseInterseptor);
      // }

    },[])

  return (
    <div className="App">
      <BrowserRouter>
      <Loader visible={loading}/>
      <Routes>
        <Route path='/*' element={<ErrorPage/>}/>
        <Route path='/' element={<LandingPage/>}/>
        <Route path='/adminlogin' element={<AdminLogin/>}/>
        <Route path='/userlogin' element={<UserLogin/>}/>
        <Route path='/adminsignup' element={<AdminRegistration/>}/>
        <Route path='/adminhomepage/*' element={<Protect Child={AdminHomePage}/>}/>
        <Route path='/userhomepage/*' element={<UserHomePage/>}/>
        <Route path={`/resetpassword`} element={<ResetPassword/>}/>
        <Route path='/adminforgotpassword' element={<ForgotPassword/>}/>
      </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
