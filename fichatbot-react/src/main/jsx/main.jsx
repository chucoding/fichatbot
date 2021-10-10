import Button from '@mui/material/Button';
import { useHistory } from "react-router-dom";

const Main = () => {

    const history = useHistory();

    //함수
    const selectStore = (store) => {
        history.push({
            pathname: "/xchat",
            props: { store:store }
        });
    };

    return (
        <div className="main">
            <h1 style={{fontSize:'8em'}}>Genie Pizza</h1>
            <Button variant="contained" onClick={()=>selectStore("KT")}>KT</Button>
            <Button variant="contained" onClick={()=>selectStore("LG")}>LG</Button>
        </div>
    );
};

export default Main;