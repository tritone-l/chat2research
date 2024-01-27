import { FC } from "react";
import styles from "./index.module.css"
import { usePageStore } from "../../store";
import FilePDF from "../FilePDF";


const Preview: FC = () => {
    const selectedFile = usePageStore((state) => state.selectedFile)

    return <div className={styles.container}>
        {
            selectedFile && <FilePDF file={selectedFile} />
        }

    </div >
}

export default Preview;