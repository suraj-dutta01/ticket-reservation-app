import { Navigate } from "react-router-dom";
import React from "react";
const Protect = ({Child}) => {
    let Admin=JSON.parse(localStorage.getItem("Admin"))
    function verify() {
        return Admin ?true:false;
    }
    return ( 
        <div className="">
            {verify()?<Child/>:<Navigate to="/adminlogin"/>}
        </div>
     );
}
 
export default Protect;