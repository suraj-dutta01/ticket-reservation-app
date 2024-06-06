import "../Styles/resetpassword.css"
const ResetPassword = () => {
    return ( 
        <div className="resetpassword">
            <form >
                <input type="text"  placeholder="Enter New Password"/>
                <input type="text"  placeholder="Enter Conform Password"/>
                <button>Set Password</button>
            </form>
        </div>
     );
}
 
export default ResetPassword;