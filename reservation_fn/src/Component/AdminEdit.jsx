import { useEffect, useState } from "react";
import "../Styles/adminedit.css"
import { useNavigate } from "react-router-dom";
import axios from "axios";
const AdminEdit = () => {
    let[name,setName]=useState("");
    let[phone,setPhone]=useState("")
    let[email,setEmail]=useState("")
    let[gst_number,setGst_number]=useState("")
    let[travels_name,setTravels_name]=useState("")
    let[password,setPassword]=useState("")
    let navigate=useNavigate()
    let data={name,phone,email,gst_number,travels_name,password}
    let Admin=JSON.parse(localStorage.getItem("Admin"))

    useEffect(()=>{
      axios.get(`http://localhost:8080/api/admins/${Admin.id}`)
      .then((res)=>{
        setName(res.data.data.name)
        setEmail(res.data.data.email)
        setPhone(res.data.data.phone)
        setGst_number(res.data.data.gst_number)
        setPassword(res.data.data.password)
        setTravels_name(res.data.data.travels_name)
        console.log("xxx");
        console.log(res.data.data.email);
      })
      .catch((err)=>{
        console.log(err);
        alert("Unable to fetch data of admin")
      })
    },[])

    function editAdmin(e) {
        e.preventDefault()
        axios.put(`http://localhost:8080/api/admins/${Admin.id}`,data)
        .then((res)=>{
            alert("Admin Updated")
        })
        .catch((err)=>{
            alert("Unable to update")
        })   
    }

    return ( 
        <div className="adminedit">
            <form action="">
                <label htmlFor="">
                    Name
                </label>
                <input type="text" value={name} required onChange={(e)=>{setName(e.target.value)}} placeholder="enter name" />
                <label htmlFor="">
                   Phone
                </label>
                <input type="text" value={phone} required onChange={(e)=>{setPhone(e.target.value)}} placeholder="enter phone" />
                <label htmlFor="">
                    Email
                </label>
                <input type="text" value={email} required onChange={(e)=>{setEmail(e.target.value)}} placeholder="enter email"/>
                <label htmlFor="">
                    GST_Number
                </label>
                <input type="text" value={gst_number} required  onChange={(e)=>{setGst_number(e.target.value)}} placeholder="enter gst_numbe"/>
                <label htmlFor="">
                    Travel's Name
                </label>
                <input type="text" value={travels_name} required onChange={(e)=>{setTravels_name(e.target.value)}} placeholder="enter travels_name"/>
                <label htmlFor="">
                    Password
                </label>
                <input type="text" value={password} required onChange={(e)=>{setPassword(e.target.value)}} placeholder="enter password " />
                <button onClick={editAdmin} className="admineditbutton">Save</button>
            </form>
        </div>
     );
}
 
export default AdminEdit;