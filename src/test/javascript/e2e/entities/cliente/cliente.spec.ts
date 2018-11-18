/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ClienteComponentsPage, ClienteDeleteDialog, ClienteUpdatePage } from './cliente.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Cliente e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let clienteUpdatePage: ClienteUpdatePage;
    let clienteComponentsPage: ClienteComponentsPage;
    let clienteDeleteDialog: ClienteDeleteDialog;
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

    it('should load Clientes', async () => {
        await navBarPage.goToEntity('cliente');
        clienteComponentsPage = new ClienteComponentsPage();
        expect(await clienteComponentsPage.getTitle()).to.eq('Clientes');
    });

    it('should load create Cliente page', async () => {
        await clienteComponentsPage.clickOnCreateButton();
        clienteUpdatePage = new ClienteUpdatePage();
        expect(await clienteUpdatePage.getPageTitle()).to.eq('Create or edit a Cliente');
        await clienteUpdatePage.cancel();
    });

    it('should create and save Clientes', async () => {
        const nbButtonsBeforeCreate = await clienteComponentsPage.countDeleteButtons();

        await clienteComponentsPage.clickOnCreateButton();
        await promise.all([
            clienteUpdatePage.setNombreInput('nombre'),
            clienteUpdatePage.setCodigoInput('codigo'),
            clienteUpdatePage.setDireccionInput('direccion'),
            clienteUpdatePage.setCorreoInput('correo'),
            clienteUpdatePage.setTelefonoInput('telefono'),
            clienteUpdatePage.setFechaDeNacimientoInput('2000-12-31'),
            clienteUpdatePage.sexoSelectLastOption(),
            clienteUpdatePage.estatusCivilSelectLastOption(),
            clienteUpdatePage.setImagenInput(absolutePath),
            clienteUpdatePage.tipoDeClienteSelectLastOption(),
            clienteUpdatePage.usuarioSelectLastOption(),
            clienteUpdatePage.tipoDeDocumentoSelectLastOption()
        ]);
        expect(await clienteUpdatePage.getNombreInput()).to.eq('nombre');
        expect(await clienteUpdatePage.getCodigoInput()).to.eq('codigo');
        expect(await clienteUpdatePage.getDireccionInput()).to.eq('direccion');
        expect(await clienteUpdatePage.getCorreoInput()).to.eq('correo');
        expect(await clienteUpdatePage.getTelefonoInput()).to.eq('telefono');
        expect(await clienteUpdatePage.getFechaDeNacimientoInput()).to.eq('2000-12-31');
        expect(await clienteUpdatePage.getImagenInput()).to.endsWith(fileNameToUpload);
        await clienteUpdatePage.save();
        expect(await clienteUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await clienteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Cliente', async () => {
        const nbButtonsBeforeDelete = await clienteComponentsPage.countDeleteButtons();
        await clienteComponentsPage.clickOnLastDeleteButton();

        clienteDeleteDialog = new ClienteDeleteDialog();
        expect(await clienteDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Cliente?');
        await clienteDeleteDialog.clickOnConfirmButton();

        expect(await clienteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
