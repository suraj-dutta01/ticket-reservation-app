import { useState } from "react";
import "../Styles/adminforgotpassword.css"
import axios from "axios";
const ForgotPassword = () => {
    let[email,setEmail]=useState("")
    function findUser(e) {
        e.preventDefault()
        axios.post(`http://localhost:8080/api/admins/forgot-password?email=${email}`)
        .then((res)=>{
            alert("A Password Reset Link Will Sended To Your Email")
        })
        .catch((err)=>{
            alert("Account Not Found")
        })
    }
    return ( 
        <div className="adminforgotpassword">
            <p>Please Enter Your Register Email Id To Reset Your Password</p>
            <form action="">
                <input type="email" value={email} onChange={(e)=>{setEmail(e.target.value)}} placeholder="Please enter email"/>
                <button onClick={findUser}>Search</button>
            </form>
        </div>
     );
}
 
export default ForgotPassword;