import * as React from 'react';
import { useState, useEffect } from 'react';
import { useLocation } from "react-router-dom";
import axios from "axios";
import { DataGrid, GridColDef, GridRowSelectionModel, GridRowId } from '@mui/x-data-grid';
import { useTranslation } from "react-i18next";
import { Box, Button, TextField, styled } from "@mui/material";
import { useStyles } from './Manager.styles';

interface Parfum {
    parfumId: number;
    nume: string;
    producator: string;
}

interface SticlaParfum {
    sticlaParfumId: number;
    volum: number;
    pret: number;
    parfum: Parfum;
}

interface Magazin {
    magazinId: number;
}

interface ParfumMagazin {
    parfumMagazinId: number;
    disponibilitate: number;
    sticlaParfum: SticlaParfum;
    magazin: Magazin;
}

const columnsMagazin: GridColDef[] = [
    { field: 'magazinId', headerName: 'Magazin ID', width: 150 },
];

const columnsParfumMagazin: GridColDef[] = [
    { field: 'parfumMagazinId', headerName: 'Parfum Magazin ID', width: 200 },
    { field: 'disponibilitate', headerName: 'Disponibilitate', width: 150 },
    { field: 'sticlaParfumId', headerName: 'Sticla Parfum ID', width: 150 },
    { field: 'numeParfum', headerName: 'Nume Parfum', width: 150 },
    { field: 'producatorParfum', headerName: 'Producator Parfum', width: 150 },
    { field: 'volum', headerName: 'Volum', width: 100 },
    { field: 'pret', headerName: 'Pret', width: 100 },
];

const StyledBox = styled(Box)(({ theme }) => ({
    margin: theme.spacing(3),
}));

export const Manager = (): JSX.Element => {
    const [magazines, setMagazines] = useState<Magazin[]>([]);
    const [selectedMagazinId, setSelectedMagazinId] = useState<number | null>(null);
    const [parfumMagazinData, setParfumMagazinData] = useState<ParfumMagazin[]>([]);
    const [selectedParfumMagazine, setSelectedParfumMagazine] = useState<ParfumMagazin | null>(null);
    const location = useLocation();
    const id = parseInt(location.state?.key, 10);

    const [nume, setNume] = useState<string>("");
    const [producator, setProducator] = useState<string>("");
    const [disponibilitate, setDisponibilitate] = useState<string>("");
    const [volum, setVolum] = useState<string>("");
    const [pret, setPret] = useState<string>("");

    const onChangeNume = (event: React.ChangeEvent<HTMLInputElement>): void => setNume(event.target.value);
    const onChangeProducator = (event: React.ChangeEvent<HTMLInputElement>): void => setProducator(event.target.value);
    const onChangeDisponibilitate = (event: React.ChangeEvent<HTMLInputElement>): void => setDisponibilitate(event.target.value);
    const onChangeVolum = (event: React.ChangeEvent<HTMLInputElement>): void => setVolum(event.target.value);
    const onChangePret = (event: React.ChangeEvent<HTMLInputElement>): void => setPret(event.target.value);

    const [t, i18n] = useTranslation("global");

    const handleChangeLanguage = (lang: string) => {
        i18n.changeLanguage(lang);
    };

    useEffect(() => {
        axios.get('http://localhost:8060/Magazin/FindAll')
            .then(response => {
                setMagazines(response.data);
            })
            .catch(error => {
                console.error(error.response.data);
                alert(`Error: ${error.response.data}`);
            });
    }, []);

    useEffect(() => {
        if (selectedMagazinId !== null) {
            axios.post('http://localhost:8060/ParfumMagazin/FindAllFromMagazin', selectedMagazinId, { headers: { "Content-Type": "application/json" } })
                .then(response => {
                    setParfumMagazinData(response.data);
                })
                .catch(error => {
                    console.error(error.response.data);
                    alert(`Error: ${error.response.data}`);
                });
        }
    }, [selectedMagazinId]);

    const handleMagazinSelectionChange = (selectionModel: GridRowSelectionModel): void => {
        if (selectionModel.length > 0) {
            const selectedMagazinId = Number(selectionModel[0]);
            setSelectedMagazinId(selectedMagazinId);
        } else {
            setSelectedMagazinId(null);
        }
    };

    const formatParfumMagazinData = (data: ParfumMagazin[]) => {
        return data.map(item => ({
            id: item.parfumMagazinId,
            ...item,
            sticlaParfumId: item.sticlaParfum.sticlaParfumId,
            numeParfum: item.sticlaParfum.parfum.nume,
            producatorParfum: item.sticlaParfum.parfum.producator,
            volum: item.sticlaParfum.volum,
            pret: item.sticlaParfum.pret,
        }));
    };

    const handleSelectionChange = (selectionModel: GridRowSelectionModel): void => {
        if (selectionModel.length > 0) {
            const selectedParfumMagazinId = Number(selectionModel[0]);
            const parfumMagazin = parfumMagazinData.find((c) => c.parfumMagazinId === selectedParfumMagazinId) || null;
            setSelectedParfumMagazine(parfumMagazin);
        } else {
            setSelectedParfumMagazine(null);
        }
    };

    const onInsert = (): void => {
        if (selectedMagazinId === null) {
            alert('Please select a magazin.');
            return;
        }

        const newParfumMagazin: ParfumMagazin = {
            parfumMagazinId: 0,
            disponibilitate: parseInt(disponibilitate),
            sticlaParfum: {
                sticlaParfumId: 0,
                volum: parseInt(volum),
                pret: parseFloat(pret),
                parfum: {
                    parfumId: 0,
                    nume: nume,
                    producator: producator,
                },
            },
            magazin: {
                magazinId: selectedMagazinId,
            },
        };

        axios.post('http://localhost:8060/ParfumMagazin/Insert', newParfumMagazin, { headers: { 'Content-Type': 'application/json' } })
            .then(response => {
                setParfumMagazinData([...parfumMagazinData, newParfumMagazin]);
            })
            .catch(error => {
                console.error(error.response.data);
                alert(`Error: ${error.response.data}`);
            });
    };

    const onUpdate = (): void => {
        if (selectedParfumMagazine === null) {
            alert('Please select a parfum magazin.');
            return;
        }

        if (selectedMagazinId === null) {
            alert('Please select a magazin.');
            return;
        }

        const updatedParfumMagazin: ParfumMagazin = {
            ...selectedParfumMagazine,
            disponibilitate: parseInt(disponibilitate) || selectedParfumMagazine.disponibilitate,
            sticlaParfum: {
                ...selectedParfumMagazine.sticlaParfum,
                volum: parseInt(volum) || selectedParfumMagazine.sticlaParfum.volum,
                pret: parseFloat(pret) || selectedParfumMagazine.sticlaParfum.pret,
                parfum: {
                    ...selectedParfumMagazine.sticlaParfum.parfum,
                    nume: nume || selectedParfumMagazine.sticlaParfum.parfum.nume,
                    producator: producator || selectedParfumMagazine.sticlaParfum.parfum.producator,
                },
            },
            magazin: {
                magazinId: selectedMagazinId,
            },
        };

        axios.post('http://localhost:8060/ParfumMagazin/Insert', updatedParfumMagazin, { headers: { 'Content-Type': 'application/json' } })
            .then(response => {
                setParfumMagazinData(parfumMagazinData.map(pm => pm.parfumMagazinId === selectedParfumMagazine.parfumMagazinId ? updatedParfumMagazin : pm));
            })
            .catch(error => {
                console.error(error.response.data);
                alert(`Error: ${error.response.data}`);
            });
    };

    const onDelete = (): void => {
        if (!selectedParfumMagazine) return;

        axios.post('http://localhost:8060/ParfumMagazin/Delete', selectedParfumMagazine, { headers: { 'Content-Type': 'application/json' } })
            .then(response => {
                setParfumMagazinData(parfumMagazinData.filter(pm => pm.parfumMagazinId !== selectedParfumMagazine.parfumMagazinId));
            })
            .catch(error => {
                console.error(error.response.data);
                alert(`Error: ${error.response.data}`);
            });
    };

    const saveAsCsv = () => {
        axios.post('http://localhost:8060/User/SaveAsCsv', id, { headers: { 'Content-Type': 'application/json' } })
            .then(response => {
                alert(response.data);
            })
            .catch(error => {
                console.error(error.response.data);
                alert(`Error: ${error.response.data}`);
            });
    };

    const saveAsJson = () => {
        axios.post('http://localhost:8060/User/SaveAsJson', id, { headers: { 'Content-Type': 'application/json' } })
            .then(response => {
                alert(response.data);
            })
            .catch(error => {
                console.error(error.response.data);
                alert(`Error: ${error.response.data}`);
            });
    };

    const saveAsDoc = () => {
        axios.post('http://localhost:8060/User/SaveAsDoc', id, { headers: { 'Content-Type': 'application/json' } })
            .then(response => {
                alert(response.data);
            })
            .catch(error => {
                console.error(error.response.data);
                alert(`Error: ${error.response.data}`);
            });
    };

    const saveAsXml = () => {
        axios.post('http://localhost:8060/User/SaveAsXml', id, { headers: { 'Content-Type': 'application/json' } })
            .then(response => {
                alert(response.data);
            })
            .catch(error => {
                console.error(error.response.data);
                alert(`Error: ${error.response.data}`);
            });
    };

    return (
        <StyledBox>
            <DataGrid
                rows={magazines.map((item) => ({ ...item, id: item.magazinId }))}
                columns={columnsMagazin}
                checkboxSelection
                onRowSelectionModelChange={(newSelection) => handleMagazinSelectionChange(newSelection)}
                getRowId={(row) => row.magazinId}
            />
            <DataGrid
                rows={formatParfumMagazinData(parfumMagazinData)}
                columns={columnsParfumMagazin}
                checkboxSelection
                onRowSelectionModelChange={(newSelection) => handleSelectionChange(newSelection)}
                getRowId={(row) => row.parfumMagazinId}
            />
            <TextField label={t('Nume')} value={nume} onChange={onChangeNume} />
            <TextField label={t('Producator')} value={producator} onChange={onChangeProducator} />
            <TextField label={t('Disponibilitate')} value={disponibilitate} onChange={onChangeDisponibilitate} />
            <TextField label={t('Volum')} value={volum} onChange={onChangeVolum} />
            <TextField label={t('Pret')} value={pret} onChange={onChangePret} />
            <Button onClick={onInsert}>{t('Insert')}</Button>
            <Button onClick={onUpdate} disabled={!selectedParfumMagazine}>{t('Update')}</Button>
            <Button onClick={onDelete} disabled={!selectedParfumMagazine}>{t('Delete')}</Button>
            <Box display="flex" justifyContent="space-between">
                <Button variant="contained" onClick={saveAsCsv}>{t('SaveAsCsv')}</Button>
                <Button variant="contained" onClick={saveAsDoc}>{t('SaveAsJson')}</Button>
                <Button variant="contained" onClick={saveAsJson}>{t('SaveAsDoc')}</Button>
                <Button variant="contained" onClick={saveAsXml}>{t('SaveAsXml')}</Button>
                <Button variant="contained" onClick={() => handleChangeLanguage("en")}>English</Button>
                <Button variant="contained" onClick={() => handleChangeLanguage("ro")}>Română</Button>
                <Button variant="contained" onClick={() => handleChangeLanguage("fr")}>Français</Button>
                <Button variant="contained" onClick={() => handleChangeLanguage("de")}>Deutsch</Button>
            </Box>
        </StyledBox>
    );
};

export default Manager;
