import { FC } from "react";
import styles from "./index.module.css"
import Sidebar from "./components/Sidebar";
import Preview from "./components/Preview";

const MainPage: FC = () => {
    return <div className={styles.page}>
        <div className={styles.page__sidebar}>
            <Sidebar />
        </div>
        <div className={styles.page__preview}>
            <Preview />
        </div>

    </div>
}

export default MainPage;