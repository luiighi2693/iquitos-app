/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { UsuarioExternoComponentsPage, UsuarioExternoDeleteDialog, UsuarioExternoUpdatePage } from './usuario-externo.page-object';

const expect = chai.expect;

describe('UsuarioExterno e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let usuarioExternoUpdatePage: UsuarioExternoUpdatePage;
    let usuarioExternoComponentsPage: UsuarioExternoComponentsPage;
    let usuarioExternoDeleteDialog: UsuarioExternoDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load UsuarioExternos', async () => {
        await navBarPage.goToEntity('usuario-externo');
        usuarioExternoComponentsPage = new UsuarioExternoComponentsPage();
        expect(await usuarioExternoComponentsPage.getTitle()).to.eq('Usuario Externos');
    });

    it('should load create UsuarioExterno page', async () => {
        await usuarioExternoComponentsPage.clickOnCreateButton();
        usuarioExternoUpdatePage = new UsuarioExternoUpdatePage();
        expect(await usuarioExternoUpdatePage.getPageTitle()).to.eq('Create or edit a Usuario Externo');
        await usuarioExternoUpdatePage.cancel();
    });

    it('should create and save UsuarioExternos', async () => {
        const nbButtonsBeforeCreate = await usuarioExternoComponentsPage.countDeleteButtons();

        await usuarioExternoComponentsPage.clickOnCreateButton();
        await promise.all([
            usuarioExternoUpdatePage.setDniInput('5'),
            usuarioExternoUpdatePage.setPinInput('5'),
            usuarioExternoUpdatePage.setRoleInput('role')
        ]);
        expect(await usuarioExternoUpdatePage.getDniInput()).to.eq('5');
        expect(await usuarioExternoUpdatePage.getPinInput()).to.eq('5');
        expect(await usuarioExternoUpdatePage.getRoleInput()).to.eq('role');
        await usuarioExternoUpdatePage.save();
        expect(await usuarioExternoUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await usuarioExternoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last UsuarioExterno', async () => {
        const nbButtonsBeforeDelete = await usuarioExternoComponentsPage.countDeleteButtons();
        await usuarioExternoComponentsPage.clickOnLastDeleteButton();

        usuarioExternoDeleteDialog = new UsuarioExternoDeleteDialog();
        expect(await usuarioExternoDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Usuario Externo?');
        await usuarioExternoDeleteDialog.clickOnConfirmButton();

        expect(await usuarioExternoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
