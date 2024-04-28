import React, { useState } from 'react';
import axios from 'axios';
import styles from './DeleteTextForm.module.css';

const DeleteTextForm = () => {
    const [value, setValue] = useState('');
    const [response, setResponse] = useState('');

    const handleInputChange = (e) => {
        setValue(e.target.value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const res = await axios.delete('http://localhost:8080/api/v1/text', {
                data: {
                    value: value,
                },
            });
            setResponse(res.data);
        } catch (error) {
            console.error('Error deleting text:', error);
            setResponse('Error deleting text');
        }
    };

    return (
        <div className={styles.formContainer}>
            <form onSubmit={handleSubmit}>
                <label className={styles.formLabel}>
                    Enter value to delete:
                    <input
                        type="text"
                        value={value}
                        onChange={handleInputChange}
                        className={styles.formInput}
                    />
                </label>
                <button type="submit" className={styles.submitButton}>
                    Delete Text
                </button>
            </form>
            {response && <p className={styles.response}>Response: {response}</p>}
        </div>
    );
};

export default DeleteTextForm;
