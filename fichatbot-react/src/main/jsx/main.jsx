import Button from '@mui/material/Button';
import { useHistory } from "react-router-dom";

const Main = () => {

    const history = useHistory();

    //함수
    const selectStore = (num) => {
        history.push({
            pathname: "/xchat",
            props: { num:num }
        });
    };

    return (
        <div className="main">
            <h1 style={{fontSize:'8em'}}>Genie Pizza</h1>
            <Button variant="contained" onClick={()=>selectStore(1)}>서울지점</Button>
            <Button variant="contained" onClick={()=>selectStore(2)}>경기지점</Button>
        </div>
    );
};

export default Main;