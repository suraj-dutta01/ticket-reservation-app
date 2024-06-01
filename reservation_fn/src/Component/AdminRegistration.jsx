import { useState } from "react";
import "../Styles/adminregistration.css"
import axios from "axios";
import { useNavigate } from "react-router-dom";
import CloseIcon from '@mui/icons-material/Close';
const AdminRegistration = () => {
    let[name,setName]=useState("");
    let[phone,setPhone]=useState("")
    let[email,setEmail]=useState("")
    let[gst_number,setGst_number]=useState("")
    let[travels_name,setTravels_name]=useState("")
    let[password,setPassword]=useState("")
    let navigate=useNavigate()
    let data={name,phone,email,gst_number,travels_name,password}

    function adminRegister(e) {
        e.preventDefault()
        axios.post("http://localhost:8080/api/admins",data)
        .then((res)=>{
            alert("admin registration successfull")
            navigate("/userlogin")
        })
        .catch((err)=>{
            alert("Admin registration failed")
        })
    }
    function exitAdminRegistration() {
        navigate("/adminlogin")
    }
    return ( 
        <div className="adminregister">
            <CloseIcon onClick={exitAdminRegistration} id="adminRegExitButton"/>
            <form action="">
                <label htmlFor="">
                    Name
                </label>
                <input type="text" required onChange={(e)=>{setName(e.target.value)}} placeholder="enter name" />
                <label htmlFor="">
                   Phone
                </label>
                <input type="text" required onChange={(e)=>{setPhone(e.target.value)}} placeholder="enter phone" />
                <label htmlFor="">
                    Email
                </label>
                <input type="text" required onChange={(e)=>{setEmail(e.target.value)}} placeholder="enter email"/>
                <label htmlFor="">
                    GST_Number
                </label>
                <input type="text" required onChange={(e)=>{setGst_number(e.target.value)}} placeholder="enter gst_numbe"/>
                <label htmlFor="">
                    Travel's Name
                </label>
                <input type="text" required onChange={(e)=>{setTravels_name(e.target.value)}} placeholder="enter travels_name"/>
                <label htmlFor="">
                    Password
                </label>
                <input type="text" required onChange={(e)=>{setPassword(e.target.value)}} placeholder="enter password " />
                <button onClick={adminRegister} className="registerButton">Register</button>
            </form>
        </div>
     );
}
 
export default AdminRegistration;