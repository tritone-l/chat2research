import { FC, useRef } from "react";
import styles from "./index.module.css"
import { usePageStore } from "../../store";
import FilePDF from "../FilePDF";


const Preview: FC = () => {
    const selectedFile = usePageStore((state) => state.selectedFile)
    const containerRef = useRef<any>(null);
    const lastFile = useRef<File | null>(null);
    if (lastFile.current !== selectedFile) {
        console.log(containerRef?.current.scrollTop);
        lastFile.current = selectedFile;
    }

    return <div className={styles.container} ref={containerRef}>
        {
            selectedFile && <FilePDF file={selectedFile} />
        }

    </div >
}

export default Preview;