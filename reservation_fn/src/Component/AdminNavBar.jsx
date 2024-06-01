import NavDropdown from "../Component/NavDropdown"
import "../Styles/adminnavbar.css"
const AdminNavBar = () => {
    return ( 
        <div className="adminnavbar">
            <div className="logo">
                <h1>Subh<span>Yatra</span></h1>
            </div>
            <div className="options">
                <NavDropdown/>
            </div>
        </div>
     );
}
 
export default AdminNavBar;