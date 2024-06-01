import {Link} from "react-router-dom"
import "../Styles/landingpage.css"
const LandingPage = () => {
    return ( 
        <div className="landingpage">
            <div className="links">
            <Link to="/adminlogin">
                <img src="https://cdn.icon-icons.com/icons2/35/PNG/96/admin_person_user_man_2839.png" alt="" />
                <h3>ADMIN</h3>
            </Link>
            <Link to="/userlogin">
                <img src="https://cdn.icon-icons.com/icons2/157/PNG/96/admin_user_man_22187.png" alt="" />
                <h3>USER</h3>
            </Link>
            </div>
        </div>
     );
}
 
export default LandingPage;