import { useState } from "react";
import "../Styles/userlogin.css"
import {Link, useNavigate} from "react-router-dom"
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";
import VisibilityIcon from "@mui/icons-material/Visibility";
import { IconButton } from "@mui/material";
import axios from "axios"
const UserLogin = () => {
    let[username,setUserName]=useState("");
    let[password,setPassword]=useState("");
    let[showPassword,setShowPassword]=useState(false);
    let navigate=useNavigate()
    
    function verify(e) {
        e.preventDefault()
        if(username.includes("@")){
          axios.post(`http://localhost:8080/api/users/verify-by-email?email=${username}&password=${password}`)
          .then((res)=>{
            navigate("/userhomepage")
            alert(`Welcome to our application ${res.data.data.name}`)
            localStorage.setItem("User",JSON.stringify(res.data.data))
          })
          .catch((err)=>{
            console.log(err);
            alert("Login Failed")
          })
        }else{
          axios.post(`http://localhost:8080/api/users/verify-by-phone?phone=${username}&password=${password}`)
          .then((res)=>{
            navigate("/userhomepage")
            alert(`Welcome to our application ${res.data.data.name}`)
            localStorage.setItem("User",JSON.stringify(res.data.data))
          })
          .catch((err)=>{
            alert("Login Failed")
          })

        }
    }
    function passwordVisiblityHandler() {
        setShowPassword(!showPassword)
    }
    
    return ( 
        <div className="userlogin">
            <form onSubmit={verify}>
                <input type="text" required value={username} onChange={(e)=>{setUserName(e.target.value)}} placeholder="Enter the Email or Phone"/>
                <input type={showPassword?"text":"password"}
                 required value={password} 
                 onChange={(e)=>{setPassword(e.target.value)}} 
                 placeholder="Enter the password"/>
                 <IconButton id="userpasswordIcon" onClick={passwordVisiblityHandler}>
                    {showPassword?<VisibilityIcon/>:<VisibilityOffIcon/>}
                 </IconButton>
                <button className="userloginButton">Login</button>
                <Link id="userforgorpass" to={"/userforgotpassword"}>Forgot Password?</Link>
                <p>Are you new a user? <Link to="/adminsignup">Register</Link></p>
            </form>
        </div>
     );
}
 
export default UserLogin;