/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EmpleadoComponentsPage, EmpleadoDeleteDialog, EmpleadoUpdatePage } from './empleado.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Empleado e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let empleadoUpdatePage: EmpleadoUpdatePage;
    let empleadoComponentsPage: EmpleadoComponentsPage;
    let empleadoDeleteDialog: EmpleadoDeleteDialog;
    const fileNameToUpload = 'logo-jhipster.png';
    const fileToUpload = '../../../../../main/webapp/content/images/' + fileNameToUpload;
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Empleados', async () => {
        await navBarPage.goToEntity('empleado');
        empleadoComponentsPage = new EmpleadoComponentsPage();
        expect(await empleadoComponentsPage.getTitle()).to.eq('Empleados');
    });

    it('should load create Empleado page', async () => {
        await empleadoComponentsPage.clickOnCreateButton();
        empleadoUpdatePage = new EmpleadoUpdatePage();
        expect(await empleadoUpdatePage.getPageTitle()).to.eq('Create or edit a Empleado');
        await empleadoUpdatePage.cancel();
    });

    it('should create and save Empleados', async () => {
        const nbButtonsBeforeCreate = await empleadoComponentsPage.countDeleteButtons();

        await empleadoComponentsPage.clickOnCreateButton();
        await promise.all([
            empleadoUpdatePage.setNombreInput('nombre'),
            empleadoUpdatePage.setApellidoInput('apellido'),
            empleadoUpdatePage.setDniInput('5'),
            empleadoUpdatePage.setDireccionInput('direccion'),
            empleadoUpdatePage.setCorreoInput('correo'),
            empleadoUpdatePage.setFechaDeNacimientoInput('2000-12-31'),
            empleadoUpdatePage.sexoSelectLastOption(),
            empleadoUpdatePage.setTelefonoInput('telefono'),
            empleadoUpdatePage.setImagenInput(absolutePath),
            empleadoUpdatePage.rolEmpleadoSelectLastOption(),
            empleadoUpdatePage.usuarioSelectLastOption()
        ]);
        expect(await empleadoUpdatePage.getNombreInput()).to.eq('nombre');
        expect(await empleadoUpdatePage.getApellidoInput()).to.eq('apellido');
        expect(await empleadoUpdatePage.getDniInput()).to.eq('5');
        expect(await empleadoUpdatePage.getDireccionInput()).to.eq('direccion');
        expect(await empleadoUpdatePage.getCorreoInput()).to.eq('correo');
        expect(await empleadoUpdatePage.getFechaDeNacimientoInput()).to.eq('2000-12-31');
        expect(await empleadoUpdatePage.getTelefonoInput()).to.eq('telefono');
        expect(await empleadoUpdatePage.getImagenInput()).to.endsWith(fileNameToUpload);
        await empleadoUpdatePage.save();
        expect(await empleadoUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await empleadoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Empleado', async () => {
        const nbButtonsBeforeDelete = await empleadoComponentsPage.countDeleteButtons();
        await empleadoComponentsPage.clickOnLastDeleteButton();

        empleadoDeleteDialog = new EmpleadoDeleteDialog();
        expect(await empleadoDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Empleado?');
        await empleadoDeleteDialog.clickOnConfirmButton();

        expect(await empleadoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
