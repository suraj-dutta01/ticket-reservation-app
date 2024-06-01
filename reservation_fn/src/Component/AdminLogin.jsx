import { useState } from "react";
import "../Styles/adminlogin.css"
import {Link, useNavigate} from "react-router-dom"
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";
import VisibilityIcon from "@mui/icons-material/Visibility";
import { IconButton } from "@mui/material";
import axios from "axios"
const AdminLogin = () => {
    let[username,setUserName]=useState("")
    let[password,setPassword]=useState("")
    let navigate=useNavigate()
    let[showPassword,setShowPassword]=useState(false)
    let[admin,setAdmin]=useState([]);
    function verify(e) {
        e.preventDefault()
        axios.post(`http://localhost:8080/api/admins/verify-by-phone?phone=${username}&password=${password}`)
        .then((res)=>{
            console.log(res.data.data.name);
            setAdmin(res.data.data);
            navigate('/adminhomepage')
            alert(`Welcome to our application ${res.data.data.name}`)
        })
        .catch((err)=>{
            alert("Admin Login Failed")
        })
    }
    function passwordVisiblityHandler() {
        setShowPassword(!showPassword)
    }
    return ( 
        <div className="adminlogin">
            <form onSubmit={verify}>
                <input type="text" required value={username} onChange={(e)=>{setUserName(e.target.value)}} placeholder="Enter the Email or Phone"/>
                <input type={showPassword?"text":"password"}
                 required value={password} 
                 onChange={(e)=>{setPassword(e.target.value)}} 
                 placeholder="Enter the password"/>
                 <IconButton id="passwordIcon" onClick={passwordVisiblityHandler}>
                    {showPassword?<VisibilityIcon/>:<VisibilityOffIcon/>}
                 </IconButton>
                <button className="loginButton">Login</button>
                <p>Are you new a user? <Link to="/adminsignup">Register</Link></p>
            </form>
        </div>
     );
}
 
export default AdminLogin;